import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GeFondCandidatModule } from './candidat/candidat.module';
import { GeFondExperienceCandidatModule } from './experience-candidat/experience-candidat.module';
import { GeFondCandidatureModule } from './candidature/candidature.module';
import { GeFondProjetModule } from './projet/projet.module';
import { GeFondVisiteModule } from './visite/visite.module';
import { GeFondSessionProjetModule } from './session-projet/session-projet.module';
import { GeFondClientModule } from './client/client.module';
import { GeFondPretModule } from './pret/pret.module';
import { GeFondElementFinancementModule } from './element-financement/element-financement.module';
import { GeFondGarantieModule } from './garantie/garantie.module';
import { GeFondEcheanceModule } from './echeance/echeance.module';
import { GeFondCompteModule } from './compte/compte.module';
import { GeFondParametrageModule } from './parametrage/parametrage.module';
import { GeFondEntretienModule } from './entretien/entretien.module';
import { GeFondChapitreModule } from './chapitre/chapitre.module';
import { GeFondMouvementModule } from './mouvement/mouvement.module';
import { GeFondDocumentModule } from './document/document.module';
import { GeFondCaisseModule } from './caisse/caisse.module';
import { GeFondOperationModule } from './operation/operation.module';
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
        GeFondElementFinancementModule,
        GeFondGarantieModule,
        GeFondEcheanceModule,
        GeFondCompteModule,
        GeFondParametrageModule,
        GeFondEntretienModule,
        GeFondChapitreModule,
        GeFondMouvementModule,
        GeFondDocumentModule,
        GeFondCaisseModule,
        GeFondOperationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondEntityModule {}
