import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    GeFondSharedLibsModule,
    GeFondSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    JhiLoginModalComponent,
    Principal,
    HasAnyAuthorityDirective,
} from '.';
import { WizardWorkflowService } from './wizard-workflow.service';
import { WizardHelperService } from './wizard-helper.service';

@NgModule({
    imports: [
        GeFondSharedLibsModule,
        GeFondSharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        WizardWorkflowService,
        WizardHelperService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        GeFondSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class GeFondSharedModule {}
