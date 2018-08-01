import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Compte } from './compte.model';
import { CompteService } from './compte.service';
import { Mouvement, MouvementService } from '../mouvement';

@Component({
    selector: 'jhi-compte-detail',
    templateUrl: './compte-detail.component.html'
})
export class CompteDetailComponent implements OnInit, OnDestroy {
    title: string = "Détail du compte";
    compte: Compte;
    mouvements:Mouvement[];
    events: string[] = ['compteListModification','mouvementListModification'];
    
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private compteService: CompteService,
        private mouvmentService: MouvementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInComptes();
    }

    emptyM(): boolean {
        return this.mouvements.length === 0;
    }

    load(id) {
        this.compteService.find(id)
            .subscribe((compteResponse: HttpResponse<Compte>) => {
                this.compte = compteResponse.body;
            });
            this.mouvmentService.query({ "compteId.equals": id}).subscribe((pretResponse: HttpResponse<Mouvement[]>) => {
                this.mouvements = pretResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }
    textColor(montant: number){
        if(montant && montant > 0) return "text-success";
        return "text-danger";
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInComptes() {
        this.events.forEach(element => {
            this.eventSubscriber = this.eventManager.subscribe(
                element,
                (response) => this.load(this.compte.id)
            );
        });
    }
}
