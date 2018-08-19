import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    ElementFinancementService,
    ElementFinancementPopupService,
    ElementFinancementComponent,
    ElementFinancementDetailComponent,
    ElementFinancementDialogComponent,
    ElementFinancementPopupComponent,
    ElementFinancementDeletePopupComponent,
    ElementFinancementDeleteDialogComponent,
    elementFinancementRoute,
    elementFinancementPopupRoute,
} from '.';
import { PretService } from '../pret';

const ENTITY_STATES = [
    ...elementFinancementRoute,
    ...elementFinancementPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ElementFinancementComponent,
        ElementFinancementDetailComponent,
        ElementFinancementDialogComponent,
        ElementFinancementDeleteDialogComponent,
        ElementFinancementPopupComponent,
        ElementFinancementDeletePopupComponent,
    ],
    entryComponents: [
        ElementFinancementComponent,
        ElementFinancementDialogComponent,
        ElementFinancementPopupComponent,
        ElementFinancementDeleteDialogComponent,
        ElementFinancementDeletePopupComponent,
    ],
    providers: [
        ElementFinancementService,
        ElementFinancementPopupService,
        PretService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondElementFinancementModule {}
