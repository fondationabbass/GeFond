import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SessionProjet } from './session-projet.model';
import { SessionProjetService } from './session-projet.service';

@Component({
    selector: 'jhi-session-projet-detail',
    templateUrl: './session-projet-detail.component.html'
})
export class SessionProjetDetailComponent implements OnInit, OnDestroy {

    sessionProjet: SessionProjet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sessionProjetService: SessionProjetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSessionProjets();
    }

    load(id) {
        this.sessionProjetService.find(id)
            .subscribe((sessionProjetResponse: HttpResponse<SessionProjet>) => {
                this.sessionProjet = sessionProjetResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSessionProjets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sessionProjetListModification',
            (response) => this.load(this.sessionProjet.id)
        );
    }
}
