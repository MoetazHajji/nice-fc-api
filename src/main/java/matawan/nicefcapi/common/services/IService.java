package matawan.nicefcapi.common.services;

import java.util.List;
import org.springframework.data.domain.Pageable;


public interface IService<Req, Res, ID> {
    Res save(Req request);
    List<Res> getAll(Pageable pageable, String sortBy);
}

