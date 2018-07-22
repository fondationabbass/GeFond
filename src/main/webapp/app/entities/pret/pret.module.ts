import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    PretService,
    PretPopupService,
    PretComponent,
    PretDetailComponent,
    PretDialogComponent,
    PretPopupComponent,
    PretDeletePopupComponent,
    PretDeleteDialogComponent,
    PretCreateComponent,
    pretRoute,
    pretPopupRoute,
    PretResolvePagingParams,
} from '.';
import { PretWzFormDataService } from './pret-wz-form-data.service';
import { PretWzWorkflowGuard } from './pret-wz-workflow-guard.service';
import { PretWzWorkflowService } from './pret-wz-workflow.service';
import { PretWzNavbarComponent } from './pret-wz-navbar.component';
import { PretWzComponent } from './pret-wz.component';
import { PretWzEcheanceComponent } from './pret-wz-echeance.component';
import { PretWzResultComponent } from './pret-wz-result.component';
import { PretWzElementFinancementComponent } from './pret-wz-element-financement.component';
import { PretWzGarantieComponent } from './pret-wz-garantie.component';

const ENTITY_STATES = [
    ...pretRoute,
    ...pretPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PretComponent,
        PretDetailComponent,
        PretDialogComponent,
        PretDeleteDialogComponent,
        PretPopupComponent,
        PretDeletePopupComponent,
        PretCreateComponent,
        PretWzNavbarComponent, 
        PretWzComponent, 
        PretWzEcheanceComponent,
        PretWzElementFinancementComponent,
        PretWzGarantieComponent, 
        PretWzResultComponent
    ],
    entryComponents: [
        PretComponent,
        PretDialogComponent,
        PretPopupComponent,
        PretDeleteDialogComponent,
        PretDeletePopupComponent,
        PretCreateComponent,
        PretWzNavbarComponent, 
        PretWzComponent, 
        PretWzEcheanceComponent,
        PretWzElementFinancementComponent,
        PretWzGarantieComponent,
        PretWzResultComponent,
    ],
    providers: [
        PretService,
        PretPopupService,
        PretResolvePagingParams,
        PretWzFormDataService,
        PretWzWorkflowGuard,
        PretWzWorkflowService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondPretModule {}
