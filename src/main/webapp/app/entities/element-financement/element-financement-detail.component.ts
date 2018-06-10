import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ElementFinancement } from './element-financement.model';
import { ElementFinancementService } from './element-financement.service';

@Component({
    selector: 'jhi-element-financement-detail',
    templateUrl: './element-financement-detail.component.html'
})
export class ElementFinancementDetailComponent implements OnInit, OnDestroy {

    elementFinancement: ElementFinancement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private elementFinancementService: ElementFinancementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInElementFinancements();
    }

    load(id) {
        this.elementFinancementService.find(id)
            .subscribe((elementFinancementResponse: HttpResponse<ElementFinancement>) => {
                this.elementFinancement = elementFinancementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInElementFinancements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'elementFinancementListModification',
            (response) => this.load(this.elementFinancement.id)
        );
    }
}
