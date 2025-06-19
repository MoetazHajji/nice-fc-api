package matawan.nicefcapi.common.services;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<Req, Res, ID> implements IService<Req, Res, ID> {

    protected abstract JpaRepository<Req, ID> getRepository();
    protected abstract Req mapToEntity(Req dto);
    protected abstract Res mapToResponse(Req entity);

    @Override
    public Res save(Req request) {
        Req entity = mapToEntity(request);
        Req saved = getRepository().save(entity);
        return mapToResponse(saved);
    }
}

