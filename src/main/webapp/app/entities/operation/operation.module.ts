import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    OperationService,
    OperationPopupService,
    OperationComponent,
    OperationDetailComponent,
    OperationDialogComponent,
    OperationPopupComponent,
    OperationDeletePopupComponent,
    OperationDeleteDialogComponent,
    operationRoute,
    operationPopupRoute,
    OperationResolvePagingParams,
    OperationRemboursementPopupService,
    OperationRemboursementPopupComponent,
    OperationRemboursementDialogComponent
} from '.';
import { PretService } from '../pret';

const ENTITY_STATES = [
    ...operationRoute,
    ...operationPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OperationComponent,
        OperationDetailComponent,
        OperationDialogComponent,
        OperationDeleteDialogComponent,
        OperationPopupComponent,
        OperationRemboursementDialogComponent,
        OperationRemboursementPopupComponent,
        OperationDeletePopupComponent,
    ],
    entryComponents: [
        OperationComponent,
        OperationDialogComponent,
        OperationPopupComponent,
        OperationRemboursementDialogComponent,
        OperationRemboursementPopupComponent,
        OperationDeleteDialogComponent,
        OperationDeletePopupComponent,
    ],
    providers: [
        OperationService,
        OperationPopupService,
        OperationRemboursementPopupService,
        OperationResolvePagingParams,
        PretService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondOperationModule {}
