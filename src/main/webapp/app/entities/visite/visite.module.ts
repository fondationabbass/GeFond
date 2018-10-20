import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    VisiteService,
    VisitePopupService,
    VisiteComponent,
    VisiteDetailComponent,
    VisiteDialogComponent,
    VisitePopupComponent,
    VisiteDeletePopupComponent,
    VisiteDeleteDialogComponent,
    visiteRoute,
    visitePopupRoute,
} from '.';
import { VisiteListComponent } from './visite-list.component';
import { VisiteEditComponent } from './visite-edit.component';

const ENTITY_STATES = [
    ...visiteRoute,
    ...visitePopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    exports: [
        VisiteEditComponent,
        VisiteListComponent
    ],
    declarations: [
        VisiteComponent,
        VisiteEditComponent,
        VisiteListComponent,
        VisiteDetailComponent,
        VisiteDialogComponent,
        VisiteDeleteDialogComponent,
        VisitePopupComponent,
        VisiteDeletePopupComponent,
    ],
    entryComponents: [
        VisiteComponent,
        VisiteDialogComponent,
        VisitePopupComponent,
        VisiteDeleteDialogComponent,
        VisiteDeletePopupComponent,
    ],
    providers: [
        VisiteService,
        VisitePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondVisiteModule {}
