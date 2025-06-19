package matawan.nicefcapi.common.services;

public interface IService<Req, Res, ID> {
    Res save(Req request);
}

