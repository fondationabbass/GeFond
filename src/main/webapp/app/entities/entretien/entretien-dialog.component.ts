import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Entretien } from './entretien.model';
import { EntretienPopupService } from './entretien-popup.service';
import { EntretienService } from './entretien.service';
import { Candidature, CandidatureService } from '../candidature';

@Component({
    selector: 'jhi-entretien-dialog',
    templateUrl: './entretien-dialog.component.html'
})
export class EntretienDialogComponent implements OnInit {

    entretien: Entretien;
    isSaving: boolean;

    candidatures: Candidature[];
    dateEntretienDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private entretienService: EntretienService,
        private candidatureService: CandidatureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.candidatureService.query()
            .subscribe((res: HttpResponse<Candidature[]>) => { this.candidatures = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entretien.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entretienService.update(this.entretien));
        } else {
            this.subscribeToSaveResponse(
                this.entretienService.create(this.entretien));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Entretien>>) {
        result.subscribe((res: HttpResponse<Entretien>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Entretien) {
        this.eventManager.broadcast({ name: 'entretienListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCandidatureById(index: number, item: Candidature) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-entretien-popup',
    template: ''
})
export class EntretienPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entretienPopupService: EntretienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.entretienPopupService
                    .open(EntretienDialogComponent as Component, params['id']);
            } else {
                this.entretienPopupService
                    .open(EntretienDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
