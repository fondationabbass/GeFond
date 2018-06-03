import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Garantie } from './garantie.model';
import { GarantieService } from './garantie.service';

@Component({
    selector: 'jhi-garantie-detail',
    templateUrl: './garantie-detail.component.html'
})
export class GarantieDetailComponent implements OnInit, OnDestroy {

    garantie: Garantie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private garantieService: GarantieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGaranties();
    }

    load(id) {
        this.garantieService.find(id)
            .subscribe((garantieResponse: HttpResponse<Garantie>) => {
                this.garantie = garantieResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGaranties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'garantieListModification',
            (response) => this.load(this.garantie.id)
        );
    }
}
