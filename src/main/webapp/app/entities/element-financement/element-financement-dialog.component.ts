import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ElementFinancement } from './element-financement.model';
import { ElementFinancementPopupService } from './element-financement-popup.service';
import { ElementFinancementService } from './element-financement.service';
import { Pret, PretService } from '../pret';
import { ParametrageService } from '../parametrage/parametrage.service';

@Component({
    selector: 'jhi-element-financement-dialog',
    templateUrl: './element-financement-dialog.component.html'
})
export class ElementFinancementDialogComponent implements OnInit {

    elementFinancement: ElementFinancement;
    isSaving: boolean;

    prets: Pret[];
    dateFinancementDp: any;
    financementTypes: string[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private elementFinancementService: ElementFinancementService,
        private pretService: PretService,
        private parametrageService: ParametrageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pretService.query()
            .subscribe((res: HttpResponse<Pret[]>) => { this.prets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.parametrageService.financementTypes().subscribe((resp: HttpResponse<string[]>) => {
                this.financementTypes = resp.body;
            });
        }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.elementFinancement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.elementFinancementService.update(this.elementFinancement));
        } else {
            this.subscribeToSaveResponse(
                this.elementFinancementService.create(this.elementFinancement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ElementFinancement>>) {
        result.subscribe((res: HttpResponse<ElementFinancement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ElementFinancement) {
        this.eventManager.broadcast({ name: 'elementFinancementListModification', content: 'OK'});
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
    selector: 'jhi-element-financement-popup',
    template: ''
})
export class ElementFinancementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private elementFinancementPopupService: ElementFinancementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.elementFinancementPopupService
                    .open(ElementFinancementDialogComponent as Component, params['id']);
            } else {
                this.elementFinancementPopupService
                    .open(ElementFinancementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
