import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Caisse } from './caisse.model';
import { CaisseService } from './caisse.service';

@Component({
    selector: 'jhi-caisse-detail',
    templateUrl: './caisse-detail.component.html'
})
export class CaisseDetailComponent implements OnInit, OnDestroy {

    caisse: Caisse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private caisseService: CaisseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCaisses();
    }

    load(id) {
        this.caisseService.find(id)
            .subscribe((caisseResponse: HttpResponse<Caisse>) => {
                this.caisse = caisseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCaisses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'caisseListModification',
            (response) => this.load(this.caisse.id)
        );
    }
}
