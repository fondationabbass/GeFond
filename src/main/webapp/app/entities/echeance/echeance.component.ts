import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Echeance } from './echeance.model';
import { EcheanceService } from './echeance.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { JhiDateUtils } from 'ng-jhipster';
import { dateToNgb } from '../../shared/model/format-utils';

@Component({
    selector: 'jhi-echeance',
    templateUrl: './echeance.component.html'
})
export class EcheanceComponent implements OnInit, OnDestroy {

currentAccount: any;
    echeances: Echeance[];
    impayes: Echeance[];
    futures: Echeance[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    linksImpayes: any;
    totalItemsImpayes: any;
    queryCountImpayes: any;
    linksFutures: any;
    totalItemsFutures: any;
    queryCountFutures: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private echeanceService: EcheanceService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private dateUtils: JhiDateUtils,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }
    loadAll() {
        this.echeanceService.query({
            //page: this.page - 1,
            "datePayement.specified": true
        //    ,
        //    size: this.itemsPerPage,
        //    sort: this.sort()
        }).subscribe(
                (res: HttpResponse<Echeance[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
        
        this.echeanceService.query({
            "datePayement.specified": false,
            "dateTombe.lessThan": this.dateUtils.convertLocalDateToServer(dateToNgb(new Date()))
            //,
            //page: this.page - 1,
            //size: this.itemsPerPage,
            //sort: this.sort()
        }).subscribe(
                (res: HttpResponse<Echeance[]>) => this.onSuccessImpayes(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.echeanceService.query({
            "datePayement.specified": false,
            "dateTombe.greaterOrEqualThan": this.dateUtils.convertLocalDateToServer(dateToNgb(new Date()))
            //,
            //page: this.page - 1,
            //size: this.itemsPerPage,
            //sort: this.sort()
        }).subscribe(
                (res: HttpResponse<Echeance[]>) => this.onSuccessFutures(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/echeance'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/echeance', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEcheances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Echeance) {
        return item.id;
    }
    registerChangeInEcheances() {
        this.eventSubscriber = this.eventManager.subscribe('echeanceListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.echeances = data;
    }
    private onSuccessImpayes(data, headers) {
        this.linksImpayes = this.parseLinks.parse(headers.get('link'));
        this.totalItemsImpayes = headers.get('X-Total-Count');
        this.queryCountImpayes = this.totalItems;
        // this.page = pagingParams.page;
        this.impayes = data;
    }
    private onSuccessFutures(data, headers) {
        this.linksFutures = this.parseLinks.parse(headers.get('link'));
        this.totalItemsFutures = headers.get('X-Total-Count');
        this.queryCountFutures = this.totalItems;
        // this.page = pagingParams.page;
        this.futures = data;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
