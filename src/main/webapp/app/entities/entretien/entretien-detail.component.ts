import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Entretien } from './entretien.model';
import { EntretienService } from './entretien.service';

@Component({
    selector: 'jhi-entretien-detail',
    templateUrl: './entretien-detail.component.html'
})
export class EntretienDetailComponent implements OnInit, OnDestroy {

    entretien: Entretien;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entretienService: EntretienService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntretiens();
    }

    load(id) {
        this.entretienService.find(id)
            .subscribe((entretienResponse: HttpResponse<Entretien>) => {
                this.entretien = entretienResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntretiens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entretienListModification',
            (response) => this.load(this.entretien.id)
        );
    }
}
