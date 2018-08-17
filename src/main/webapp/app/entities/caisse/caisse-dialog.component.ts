import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Caisse } from './caisse.model';
import { CaissePopupService } from './caisse-popup.service';
import { CaisseService } from './caisse.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-caisse-dialog',
    templateUrl: './caisse-dialog.component.html'
})
export class CaisseDialogComponent implements OnInit {

    caisse: Caisse;
    isSaving: boolean;

    users: User[];
    dateOuvertureDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private caisseService: CaisseService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.caisse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.caisseService.update(this.caisse));
        } else {
            this.subscribeToSaveResponse(
                this.caisseService.create(this.caisse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Caisse>>) {
        result.subscribe((res: HttpResponse<Caisse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Caisse) {
        this.eventManager.broadcast({ name: 'caisseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-caisse-popup',
    template: ''
})
export class CaissePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private caissePopupService: CaissePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.caissePopupService
                    .open(CaisseDialogComponent as Component, params['id']);
            } else {
                this.caissePopupService
                    .open(CaisseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
