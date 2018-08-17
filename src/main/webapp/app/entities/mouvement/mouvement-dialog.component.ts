import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mouvement } from './mouvement.model';
import { MouvementPopupService } from './mouvement-popup.service';
import { MouvementService } from './mouvement.service';
import { Compte, CompteService } from '../compte';
import { Operation, OperationService } from '../operation';

@Component({
    selector: 'jhi-mouvement-dialog',
    templateUrl: './mouvement-dialog.component.html'
})
export class MouvementDialogComponent implements OnInit {

    mouvement: Mouvement;
    isSaving: boolean;

    comptes: Compte[];

    operations: Operation[];
    dateMvtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mouvementService: MouvementService,
        private compteService: CompteService,
        private operationService: OperationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.compteService.query()
            .subscribe((res: HttpResponse<Compte[]>) => { this.comptes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.operationService.query()
            .subscribe((res: HttpResponse<Operation[]>) => { this.operations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mouvement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mouvementService.update(this.mouvement));
        } else {
            this.subscribeToSaveResponse(
                this.mouvementService.create(this.mouvement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Mouvement>>) {
        result.subscribe((res: HttpResponse<Mouvement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Mouvement) {
        this.eventManager.broadcast({ name: 'mouvementListModification', content: 'OK'});
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

    trackOperationById(index: number, item: Operation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mouvement-popup',
    template: ''
})
export class MouvementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mouvementPopupService: MouvementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mouvementPopupService
                    .open(MouvementDialogComponent as Component, params['id']);
            } else {
                this.mouvementPopupService
                    .open(MouvementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
