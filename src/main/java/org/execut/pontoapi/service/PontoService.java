package org.execut.pontoapi.service;

import org.execut.pontoapi.dto.NovaBatidaMobileDTO;
import org.execut.pontoapi.model.BatidaPonto;
import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.repository.BatidaPontoRepository;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PontoService {

    private final BatidaPontoRepository repository;
    private final ColaboradorRepository colaboradorRepository; // Injetado para buscar o colaborador

    public PontoService(BatidaPontoRepository repository, ColaboradorRepository colaboradorRepository) {
        this.repository = repository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public BatidaPonto processarNovaBatida(NovaBatidaMobileDTO dto) {
        BatidaPonto batida = new BatidaPonto();

        // ERRO 1 (Linha 21) RESOLVIDO: Busca o objeto Colaborador e insere na batida
        Colaborador colaborador = colaboradorRepository.findByMatricula(dto.getMatriculaColaborador())
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado: " + dto.getMatriculaColaborador()));

        batida.setColaborador(colaborador);

        batida.setLatitude(dto.getLatitude());
        batida.setLongitude(dto.getLongitude());
        batida.setDeviceId(dto.getDeviceId());
        batida.setRegistroOffline(dto.isOffline());

        // ERRO 2 (Linha 28) RESOLVIDO: Usando o nome correto que definimos antes
        batida.setHoraBatida(dto.getDataHoraNtp() != null ? dto.getDataHoraNtp() : LocalDateTime.now());

        batida.setDataLimiteRetencaoLgpd(LocalDateTime.now().plusYears(5));

        String nomeArquivo = "selfie_" + dto.getMatriculaColaborador() + "_" + System.currentTimeMillis() + ".jpg";
        batida.setCaminhoFotoSelfie("/uploads/fotos/" + nomeArquivo);

        return repository.save(batida);
    }

    public List<BatidaPonto> listarTodasBatidas() {
        return repository.findAll();
    }
}