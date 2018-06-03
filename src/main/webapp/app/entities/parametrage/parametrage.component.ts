import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Parametrage } from './parametrage.model';
import { ParametrageService } from './parametrage.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-parametrage',
    templateUrl: './parametrage.component.html'
})
export class ParametrageComponent implements OnInit, OnDestroy {
parametrages: Parametrage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private parametrageService: ParametrageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.parametrageService.query().subscribe(
            (res: HttpResponse<Parametrage[]>) => {
                this.parametrages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInParametrages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Parametrage) {
        return item.id;
    }
    registerChangeInParametrages() {
        this.eventSubscriber = this.eventManager.subscribe('parametrageListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
