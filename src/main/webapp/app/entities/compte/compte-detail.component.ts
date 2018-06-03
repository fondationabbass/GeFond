import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Compte } from './compte.model';
import { CompteService } from './compte.service';

@Component({
    selector: 'jhi-compte-detail',
    templateUrl: './compte-detail.component.html'
})
export class CompteDetailComponent implements OnInit, OnDestroy {

    compte: Compte;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private compteService: CompteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInComptes();
    }

    load(id) {
        this.compteService.find(id)
            .subscribe((compteResponse: HttpResponse<Compte>) => {
                this.compte = compteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInComptes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'compteListModification',
            (response) => this.load(this.compte.id)
        );
    }
}
