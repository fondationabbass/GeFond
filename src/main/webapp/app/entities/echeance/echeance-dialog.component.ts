import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Echeance } from './echeance.model';
import { EcheancePopupService } from './echeance-popup.service';
import { EcheanceService } from './echeance.service';
import { Pret, PretService } from '../pret';
import { Mouvement, MouvementService } from '../mouvement';

@Component({
    selector: 'jhi-echeance-dialog',
    templateUrl: './echeance-dialog.component.html'
})
export class EcheanceDialogComponent implements OnInit {

    echeance: Echeance;
    isSaving: boolean;

    prets: Pret[];

    mouvements: Mouvement[];
    dateTombeDp: any;
    datePayementDp: any;
    dateRetraitDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private echeanceService: EcheanceService,
        private pretService: PretService,
        private mouvementService: MouvementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pretService.query()
            .subscribe((res: HttpResponse<Pret[]>) => { this.prets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.mouvementService.query()
            .subscribe((res: HttpResponse<Mouvement[]>) => { this.mouvements = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.echeance.id !== undefined) {
            this.subscribeToSaveResponse(
                this.echeanceService.update(this.echeance));
        } else {
            this.subscribeToSaveResponse(
                this.echeanceService.create(this.echeance));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Echeance>>) {
        result.subscribe((res: HttpResponse<Echeance>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Echeance) {
        this.eventManager.broadcast({ name: 'echeanceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPretById(index: number, item: Pret) {
        return item.id;
    }

    trackMouvementById(index: number, item: Mouvement) {
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
    selector: 'jhi-echeance-popup',
    template: ''
})
export class EcheancePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private echeancePopupService: EcheancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.echeancePopupService
                    .open(EcheanceDialogComponent as Component, params['id']);
            } else {
                this.echeancePopupService
                    .open(EcheanceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
