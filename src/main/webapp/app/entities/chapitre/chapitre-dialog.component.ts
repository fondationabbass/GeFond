import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Chapitre } from './chapitre.model';
import { ChapitrePopupService } from './chapitre-popup.service';
import { ChapitreService } from './chapitre.service';

@Component({
    selector: 'jhi-chapitre-dialog',
    templateUrl: './chapitre-dialog.component.html'
})
export class ChapitreDialogComponent implements OnInit {

    chapitre: Chapitre;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private chapitreService: ChapitreService,
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
        if (this.chapitre.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chapitreService.update(this.chapitre));
        } else {
            this.subscribeToSaveResponse(
                this.chapitreService.create(this.chapitre));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Chapitre>>) {
        result.subscribe((res: HttpResponse<Chapitre>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Chapitre) {
        this.eventManager.broadcast({ name: 'chapitreListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-chapitre-popup',
    template: ''
})
export class ChapitrePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chapitrePopupService: ChapitrePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.chapitrePopupService
                    .open(ChapitreDialogComponent as Component, params['id']);
            } else {
                this.chapitrePopupService
                    .open(ChapitreDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
