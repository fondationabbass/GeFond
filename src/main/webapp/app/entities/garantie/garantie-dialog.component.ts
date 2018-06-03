import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Garantie } from './garantie.model';
import { GarantiePopupService } from './garantie-popup.service';
import { GarantieService } from './garantie.service';
import { Pret, PretService } from '../pret';

@Component({
    selector: 'jhi-garantie-dialog',
    templateUrl: './garantie-dialog.component.html'
})
export class GarantieDialogComponent implements OnInit {

    garantie: Garantie;
    isSaving: boolean;

    prets: Pret[];
    dateDepotDp: any;
    dateRetraitDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private garantieService: GarantieService,
        private pretService: PretService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pretService.query()
            .subscribe((res: HttpResponse<Pret[]>) => { this.prets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.garantie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.garantieService.update(this.garantie));
        } else {
            this.subscribeToSaveResponse(
                this.garantieService.create(this.garantie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Garantie>>) {
        result.subscribe((res: HttpResponse<Garantie>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Garantie) {
        this.eventManager.broadcast({ name: 'garantieListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-garantie-popup',
    template: ''
})
export class GarantiePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private garantiePopupService: GarantiePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.garantiePopupService
                    .open(GarantieDialogComponent as Component, params['id']);
            } else {
                this.garantiePopupService
                    .open(GarantieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
