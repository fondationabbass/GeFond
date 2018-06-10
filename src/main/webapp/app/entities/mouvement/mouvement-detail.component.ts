import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Mouvement } from './mouvement.model';
import { MouvementService } from './mouvement.service';

@Component({
    selector: 'jhi-mouvement-detail',
    templateUrl: './mouvement-detail.component.html'
})
export class MouvementDetailComponent implements OnInit, OnDestroy {

    mouvement: Mouvement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mouvementService: MouvementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMouvements();
    }

    load(id) {
        this.mouvementService.find(id)
            .subscribe((mouvementResponse: HttpResponse<Mouvement>) => {
                this.mouvement = mouvementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMouvements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mouvementListModification',
            (response) => this.load(this.mouvement.id)
        );
    }
}
