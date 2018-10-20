import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    ProjetService,
    ProjetPopupService,
    ProjetComponent,
    ProjetDetailComponent,
    ProjetDialogComponent,
    ProjetPopupComponent,
    ProjetDeletePopupComponent,
    ProjetDeleteDialogComponent,
    projetRoute,
    projetPopupRoute,
    ProjetResolvePagingParams,
} from '.';
import { ProjetEditComponent } from './projet-edit.component';

const ENTITY_STATES = [
    ...projetRoute,
    ...projetPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    exports: [
        ProjetEditComponent,
        ProjetDetailComponent
    ],
    declarations: [
        ProjetEditComponent,
        ProjetComponent,
        ProjetDetailComponent,
        ProjetDialogComponent,
        ProjetDeleteDialogComponent,
        ProjetPopupComponent,
        ProjetDeletePopupComponent,
    ],
    entryComponents: [
        ProjetComponent,
        ProjetDialogComponent,
        ProjetPopupComponent,
        ProjetDeleteDialogComponent,
        ProjetDeletePopupComponent,
    ],
    providers: [
        ProjetService,
        ProjetPopupService,
        ProjetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondProjetModule {}
