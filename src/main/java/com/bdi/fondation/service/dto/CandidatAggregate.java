package com.bdi.fondation.service.dto;

import java.util.Arrays;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.domain.ExperienceCandidat;

public class CandidatAggregate {
	private Candidat candidat;
	private ExperienceCandidat[] exps;
    public Candidat getCandidat() {
        return candidat;
    }
    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }
    public ExperienceCandidat[] getExps() {
        return exps;
    }
    public void setExps(ExperienceCandidat[] exps) {
        this.exps = exps;
    }
    @Override
    public String toString() {
        return "CandidatAggregate [candidat=" + candidat + ", exps=" + Arrays.toString(exps) + "]";
    }

}
