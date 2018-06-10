import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Parametrage } from './parametrage.model';
import { ParametrageService } from './parametrage.service';

@Component({
    selector: 'jhi-parametrage-detail',
    templateUrl: './parametrage-detail.component.html'
})
export class ParametrageDetailComponent implements OnInit, OnDestroy {

    parametrage: Parametrage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private parametrageService: ParametrageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParametrages();
    }

    load(id) {
        this.parametrageService.find(id)
            .subscribe((parametrageResponse: HttpResponse<Parametrage>) => {
                this.parametrage = parametrageResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParametrages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parametrageListModification',
            (response) => this.load(this.parametrage.id)
        );
    }
}
