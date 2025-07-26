package vn.flast.entities.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
public class TagsRequest {
    private List<String> tags;
}
