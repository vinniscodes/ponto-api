package org.execut.pontoapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "filiais")
public class Filial {

    @Id
    private String id; // A chave do tenant (Ex: "JEEP789")

    private String nome;
    private String descricao;
    private String icone;

    // Configurações de Tema (White-Label)
    private String corBg;
    private String corText;
    private String corPrimary;
    private String corCard;
    private String corBorder;

    // ==========================================
    // GETTERS E SETTERS MANUAIS (Garante que o Java encontre os métodos)
    // ==========================================

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getIcone() { return icone; }
    public void setIcone(String icone) { this.icone = icone; }

    public String getCorBg() { return corBg; }
    public void setCorBg(String corBg) { this.corBg = corBg; }

    public String getCorText() { return corText; }
    public void setCorText(String corText) { this.corText = corText; }

    public String getCorPrimary() { return corPrimary; }
    public void setCorPrimary(String corPrimary) { this.corPrimary = corPrimary; }

    public String getCorCard() { return corCard; }
    public void setCorCard(String corCard) { this.corCard = corCard; }

    public String getCorBorder() { return corBorder; }
    public void setCorBorder(String corBorder) { this.corBorder = corBorder; }
}