import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GeFondCandidatModule } from './candidat/candidat.module';
import { GeFondExperienceCandidatModule } from './experience-candidat/experience-candidat.module';
import { GeFondCandidatureModule } from './candidature/candidature.module';
import { GeFondProjetModule } from './projet/projet.module';
import { GeFondVisiteModule } from './visite/visite.module';
import { GeFondSessionProjetModule } from './session-projet/session-projet.module';
import { GeFondClientModule } from './client/client.module';
import { GeFondPretModule } from './pret/pret.module';
import { GeFondGarantieModule } from './garantie/garantie.module';
import { GeFondEcheanceModule } from './echeance/echeance.module';
import { GeFondCompteModule } from './compte/compte.module';
import { GeFondParametrageModule } from './parametrage/parametrage.module';
import { GeFondEntretienModule } from './entretien/entretien.module';
import { GeFondChapitreModule } from './chapitre/chapitre.module';
import { GeFondMouvementModule } from './mouvement/mouvement.module';
import { GeFondDocumentModule } from './document/document.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GeFondCandidatModule,
        GeFondExperienceCandidatModule,
        GeFondCandidatureModule,
        GeFondProjetModule,
        GeFondVisiteModule,
        GeFondSessionProjetModule,
        GeFondClientModule,
        GeFondPretModule,
        GeFondGarantieModule,
        GeFondEcheanceModule,
        GeFondCompteModule,
        GeFondParametrageModule,
        GeFondEntretienModule,
        GeFondChapitreModule,
        GeFondMouvementModule,
        GeFondDocumentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondEntityModule {}
