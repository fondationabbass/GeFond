import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Operation } from './operation.model';
import { OperationPopupService } from './operation-popup.service';
import { OperationService } from './operation.service';
import { Compte, CompteService } from '../compte';
import { Pret, PretService } from '../pret';
import { Caisse, CaisseService } from '../caisse';
import { Echeance, EcheanceService } from '../echeance';

@Component({
    selector: 'jhi-operation-dialog',
    templateUrl: './operation-dialog.component.html'
})
export class OperationDialogComponent implements OnInit {

    operation: Operation;
    isSaving: boolean;

    comptes: Compte[];

    prets: Pret[];

    caisses: Caisse[];

    echeances: Echeance[];
    dateOperationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private operationService: OperationService,
        private compteService: CompteService,
        private pretService: PretService,
        private caisseService: CaisseService,
        private echeanceService: EcheanceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.compteService.query()
            .subscribe((res: HttpResponse<Compte[]>) => { this.comptes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.pretService.query()
            .subscribe((res: HttpResponse<Pret[]>) => { this.prets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.caisseService.query()
            .subscribe((res: HttpResponse<Caisse[]>) => { this.caisses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.echeanceService.query()
            .subscribe((res: HttpResponse<Echeance[]>) => { this.echeances = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operationService.update(this.operation));
        } else {
            this.subscribeToSaveResponse(
                this.operationService.create(this.operation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Operation>>) {
        result.subscribe((res: HttpResponse<Operation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Operation) {
        this.eventManager.broadcast({ name: 'operationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCompteById(index: number, item: Compte) {
        return item.id;
    }

    trackPretById(index: number, item: Pret) {
        return item.id;
    }

    trackCaisseById(index: number, item: Caisse) {
        return item.id;
    }

    trackEcheanceById(index: number, item: Echeance) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-operation-popup',
    template: ''
})
export class OperationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationPopupService: OperationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.operationPopupService
                    .open(OperationDialogComponent as Component, params['id']);
            } else {
                this.operationPopupService
                    .open(OperationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
