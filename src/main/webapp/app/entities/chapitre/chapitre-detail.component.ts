import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Chapitre } from './chapitre.model';
import { ChapitreService } from './chapitre.service';

@Component({
    selector: 'jhi-chapitre-detail',
    templateUrl: './chapitre-detail.component.html'
})
export class ChapitreDetailComponent implements OnInit, OnDestroy {

    chapitre: Chapitre;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private chapitreService: ChapitreService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChapitres();
    }

    load(id) {
        this.chapitreService.find(id)
            .subscribe((chapitreResponse: HttpResponse<Chapitre>) => {
                this.chapitre = chapitreResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChapitres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'chapitreListModification',
            (response) => this.load(this.chapitre.id)
        );
    }
}
