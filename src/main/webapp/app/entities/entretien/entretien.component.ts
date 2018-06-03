import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Entretien } from './entretien.model';
import { EntretienService } from './entretien.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-entretien',
    templateUrl: './entretien.component.html'
})
export class EntretienComponent implements OnInit, OnDestroy {
entretiens: Entretien[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private entretienService: EntretienService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.entretienService.query().subscribe(
            (res: HttpResponse<Entretien[]>) => {
                this.entretiens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEntretiens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Entretien) {
        return item.id;
    }
    registerChangeInEntretiens() {
        this.eventSubscriber = this.eventManager.subscribe('entretienListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
