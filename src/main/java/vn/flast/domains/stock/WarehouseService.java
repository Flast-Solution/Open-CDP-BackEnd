package vn.flast.domains.stock;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.components.RecordNotFoundException;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.warehouse.Delivery;
import vn.flast.entities.warehouse.SaveStock;
import vn.flast.entities.warehouse.SkuDetails;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.*;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.*;
import vn.flast.searchs.WarehouseFilter;
import vn.flast.utils.*;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final WarehouseProductRepository wareHouseRepository;
    private final WareHouseStatusRepository wareHouseStatusRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final WarehouseExchangeRepository exportRepository;

    private final TransporterRepository transporterRepository;
    private final ShippingHistoryRepository shippingHistoryRepository;

    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;

    private final OrderService orderService;
    private final CustomerOrderRepository orderRepository;

    public WarehouseProduct created(SaveStock saveStock) {
        var input = saveStock.model();
        input.setUserName(Common.getSsoId());
        input.setSkuInfo(JsonUtils.toJson(saveStock.mSkuDetails()));

        WareHouseStock stock = warehouseStockRepository.findById(input.getStockId()).orElseThrow(
            () -> new RuntimeException("Kho không tồn tại !")
        );

        String hash = StockUtils.hashSku(input.getSkuInfo());
        input.setSkuHash(hash);
        input.setStockName(stock.getName());
        return wareHouseRepository.save(input);
    }

    public WarehouseProduct updated(SaveStock saveStock) {
        var input = saveStock.model();
        var warehouse = wareHouseRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, warehouse);

        WareHouseStock stock = warehouseStockRepository.findById(input.getStockId()).orElseThrow(
            () -> new RuntimeException("Kho không tồn tại !")
        );
        warehouse.setStockName(stock.getName());
        warehouse.setSkuInfo(JsonUtils.toJson(saveStock.mSkuDetails()));

        String hash = StockUtils.hashSku(warehouse.getSkuInfo());
        warehouse.setSkuHash(hash);
        return wareHouseRepository.save(warehouse);
    }

    @Transactional(rollbackFor = Exception.class)
    public WarehouseExchange exchange(WarehouseExchange model) {
        if(model.getWarehouseTargetId().equals(model.getWarehouseSourceId())) {
            throw new RuntimeException("Chọn cùng kho là không được phép");
        }
        WarehouseProduct modelWHSource = wareHouseRepository.findById(model.getWarehouseSourceId()).orElseThrow(
            () -> new RecordNotFoundException("Kho nguồn không tồn tại")
        );
        if(modelWHSource.getQuantity() < model.getQuantity()) {
            throw new RuntimeException("Số lượng đã vượt quá trong kho");
        }
        var stock = warehouseStockRepository.findById(model.getWarehouseTargetId()).orElseThrow(
            () -> new ResourceNotFoundException("Kho không tồn tại")
        );

        var listSkus = wareHouseRepository.findBySkuAndStockId(modelWHSource.getSkuId(), model.getWarehouseTargetId());
        var entity = listSkus.stream()
            .filter(i -> i.getSkuInfo().equals(modelWHSource.getSkuInfo()))
            .findFirst()
            .orElseGet(WarehouseProduct::new);
        if(NumberUtils.isNull(entity.getId())) {
            CopyProperty.CopyIgnoreNull(modelWHSource, entity, "id");
            entity.setQuantity(model.getQuantity());
            entity.setStockId(stock.getId());
            entity.setStockName(stock.getName());
            entity.setTotal(model.getQuantity());
            entity.setUserName(Common.getSsoId());
        } else {
            entity.setQuantity(model.getQuantity() + entity.getQuantity());
            entity.setTotal(model.getQuantity() + entity.getTotal());
        }
        modelWHSource.setQuantity(modelWHSource.getQuantity() - model.getQuantity());
        wareHouseRepository.save(modelWHSource);
        wareHouseRepository.save(entity);

        model.setSsoId(Common.getSsoId());
        return exportRepository.save(model);
    }

    public void appendFieldTransient(List<WarehouseProduct> lists) {
        List<Long> pIds = lists.stream().map(WarehouseProduct::getProductId).toList();
        List<Product> products = productRepository.findByListId(pIds);
        Map<Long, Product> mProducts = MapUtils.toIdentityMap(products, Product::getId);

        List<Long> providerIds = lists.stream().map(WarehouseProduct::getProviderId).toList();
        List<Provider> providers = providerRepository.findByListId(providerIds);
        Map<Long, String> mProviders = MapUtils.mapKeyValue(providers, Provider::getId, Provider::getName);

        for(WarehouseProduct whProduct : lists) {
            whProduct.setSkuDetails(JsonUtils.Json2ListObject(whProduct.getSkuInfo(), SkuDetails.class));
            Product product = mProducts.get(whProduct.getProductId());
            whProduct.setProduct(product);
            String mPName = mProviders.get(whProduct.getProviderId());
            whProduct.setProviderName(mPName);
        }
    }

    public Ipage<?> fetch(WarehouseFilter filter) {
        int LIMIT = filter.limit();
        int currentPage = filter.page();

        var et = EntityQuery.create(entityManager, WarehouseProduct.class);
        et.addDescendingOrderBy("id")
            .integerEqualsTo("productId", filter.productId())
            .integerEqualsTo("skuId", filter.skuId())
            .integerEqualsTo("providerId", filter.providerId())
            .integerEqualsTo("stockId", filter.stockId())
            .setMaxResults(LIMIT)
            .setFirstResult(LIMIT * currentPage);

        if(StringUtils.isNotNull(filter.skuHash())) {
            et.stringEqualsTo("skuHash", filter.skuHash());
        }
        var lists = et.list();

        appendFieldTransient(lists);
        return Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        var data = wareHouseRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        wareHouseRepository.delete(data);
    }

    public WareHouseStatus createStatus(WareHouseStatus input) {
        if(wareHouseStatusRepository.existsByName(input.getName())){
            throw new RuntimeException("Trạng thái kho đã tồn tại rồi !");
        }
        return wareHouseStatusRepository.save(input);
    }

    public WarehouseProduct findById(Integer id) {
        return wareHouseRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi WarehouseProduct không tồn tại !")
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public ShippingHistory delivery(Delivery input){
        ShippingHistory history = input.transformShip();
        var transporter = transporterRepository.findById(input.transporterId()).orElseThrow(
            () -> new RecordNotFoundException("Không tìm thấy đơn vị vận chuyển")
        );
        history.setSsoId(Common.getSsoId());
        history.setTransportName(transporter.getName());
        var warehouse = wareHouseRepository.findById(history.getWarehouseId()).orElseThrow(
            () -> new RecordNotFoundException("Không tìm thấy tồn kho")
        );
        warehouse.setQuantity(warehouse.getQuantity() - history.getQuantity());

        wareHouseRepository.save(warehouse);
        shippingHistoryRepository.save(history);

        var order = orderRepository.findByCode(input.orderCode()).orElseThrow(
            () -> new ResourceNotFoundException("Order not found .!")
        );
        if(StringUtils.isNull(order.getCustomerAddress())) {
            order.setCustomerAddress(history.getAddress());
            order.setCustomerProvinceId(history.getProvinceId());
            order.setCustomerWardId(history.getWardId());
            orderService.save(order);
        }
        return history;
    }

    public void createStock(WareHouseStock input){
        if(warehouseStockRepository.existsByName(input.getName())){
            return;
        }
        warehouseStockRepository.save(input);
    }

    public List<WareHouseStock> fetchStock(){
        return warehouseStockRepository.findAll();
    }

    public void updateStock(WareHouseStock input){
        var stock = warehouseStockRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("record does not exist")
        );
        CopyProperty.CopyIgnoreNull(input, stock);
        warehouseStockRepository.save(stock);
    }

    public WareHouseStock findStockById(Integer id) {
        return warehouseStockRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi WareHouseStock không tồn tại !")
        );
    }
}
