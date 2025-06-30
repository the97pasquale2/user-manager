package demo.usermanager.presentation.common;

import demo.usermanager.auth.SearchHideFor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ListDto<T> {
    @SearchHideFor
    private List<T> list;
}
