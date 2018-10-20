import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeFondSharedModule } from '../../shared';
import {
    DocumentService,
    DocumentPopupService,
    DocumentComponent,
    DocumentDetailComponent,
    DocumentDialogComponent,
    DocumentPopupComponent,
    DocumentDeletePopupComponent,
    DocumentDeleteDialogComponent,
    documentRoute,
    documentPopupRoute,
} from '.';
import { DocumentEditComponent } from './document-edit.component';
import { DocumentListComponent } from './document-list.component';

const ENTITY_STATES = [
    ...documentRoute,
    ...documentPopupRoute,
];

@NgModule({
    imports: [
        GeFondSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    exports: [
        DocumentEditComponent,
        DocumentListComponent
    ],
    declarations: [
        DocumentComponent,
        DocumentEditComponent,
        DocumentListComponent,
        DocumentDetailComponent,
        DocumentDialogComponent,
        DocumentDeleteDialogComponent,
        DocumentPopupComponent,
        DocumentDeletePopupComponent,
    ],
    entryComponents: [
        DocumentComponent,
        DocumentDialogComponent,
        DocumentPopupComponent,
        DocumentDeleteDialogComponent,
        DocumentDeletePopupComponent,
    ],
    providers: [
        DocumentService,
        DocumentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeFondDocumentModule {}
