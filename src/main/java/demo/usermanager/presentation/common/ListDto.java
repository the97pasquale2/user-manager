package demo.usermanager.presentation.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ListDto<T> {
    private List<T> list;
}
