import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parametrage } from './parametrage.model';
import { ParametragePopupService } from './parametrage-popup.service';
import { ParametrageService } from './parametrage.service';

@Component({
    selector: 'jhi-parametrage-dialog',
    templateUrl: './parametrage-dialog.component.html'
})
export class ParametrageDialogComponent implements OnInit {

    parametrage: Parametrage;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private parametrageService: ParametrageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.parametrage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parametrageService.update(this.parametrage));
        } else {
            this.subscribeToSaveResponse(
                this.parametrageService.create(this.parametrage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Parametrage>>) {
        result.subscribe((res: HttpResponse<Parametrage>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Parametrage) {
        this.eventManager.broadcast({ name: 'parametrageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-parametrage-popup',
    template: ''
})
export class ParametragePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametragePopupService: ParametragePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parametragePopupService
                    .open(ParametrageDialogComponent as Component, params['id']);
            } else {
                this.parametragePopupService
                    .open(ParametrageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
