package vn.flast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.models.DataCollection;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.DataCollectionRepository;
import vn.flast.searchs.DataCollectionFilter;

@Service
@RequiredArgsConstructor
public class DataCollectionService {

    private final DataCollectionRepository collectionRepository;

    public DataCollection save(DataCollection input) {
        collectionRepository.save(input);
        return input;
    }

    public Ipage<DataCollection> fetch(DataCollectionFilter filter) {
        return Ipage.empty();
    }
}
