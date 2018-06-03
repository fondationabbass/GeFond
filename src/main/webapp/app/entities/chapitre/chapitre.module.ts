import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    ChapitreService,
    ChapitrePopupService,
    ChapitreComponent,
    ChapitreDetailComponent,
    ChapitreDialogComponent,
    ChapitrePopupComponent,
    ChapitreDeletePopupComponent,
    ChapitreDeleteDialogComponent,
    chapitreRoute,
    chapitrePopupRoute,
} from './';

const ENTITY_STATES = [
    ...chapitreRoute,
    ...chapitrePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ChapitreComponent,
        ChapitreDetailComponent,
        ChapitreDialogComponent,
        ChapitreDeleteDialogComponent,
        ChapitrePopupComponent,
        ChapitreDeletePopupComponent,
    ],
    entryComponents: [
        ChapitreComponent,
        ChapitreDialogComponent,
        ChapitrePopupComponent,
        ChapitreDeleteDialogComponent,
        ChapitreDeletePopupComponent,
    ],
    providers: [
        ChapitreService,
        ChapitrePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondChapitreModule {}
