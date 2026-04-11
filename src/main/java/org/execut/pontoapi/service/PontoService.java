package org.execut.pontoapi.service;

import org.execut.pontoapi.dto.NovaBatidaMobileDTO;
import org.execut.pontoapi.model.BatidaPonto;
import org.execut.pontoapi.repository.BatidaPontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PontoService {

    @Autowired
    private BatidaPontoRepository repository;

    public BatidaPonto registrarBatida(NovaBatidaMobileDTO dto, String tenantId) {
        BatidaPonto batida = new BatidaPonto();
        batida.setTenantId(tenantId);
        batida.setMatricula(dto.getMatricula());
        batida.setNomeFuncionario(dto.getNomeFuncionario());
        batida.setLatitude(dto.getLatitude());
        batida.setLongitude(dto.getLongitude());
        batida.setIsMocked(dto.getIsMocked());
        batida.setOffline(dto.getOffline());
        batida.setFotoBase64(dto.getFotoBase64());
        batida.setDeviceId(dto.getDeviceId());

        // Converte a string ISO do celular para LocalDateTime do Java
        LocalDateTime dataHoraConvertida = LocalDateTime.parse(dto.getDataHora(), DateTimeFormatter.ISO_DATE_TIME);
        batida.setDataHora(dataHoraConvertida);

        return repository.save(batida);
    }
}