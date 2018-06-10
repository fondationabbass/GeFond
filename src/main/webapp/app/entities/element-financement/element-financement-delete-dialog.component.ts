import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ElementFinancement } from './element-financement.model';
import { ElementFinancementPopupService } from './element-financement-popup.service';
import { ElementFinancementService } from './element-financement.service';

@Component({
    selector: 'jhi-element-financement-delete-dialog',
    templateUrl: './element-financement-delete-dialog.component.html'
})
export class ElementFinancementDeleteDialogComponent {

    elementFinancement: ElementFinancement;

    constructor(
        private elementFinancementService: ElementFinancementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.elementFinancementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'elementFinancementListModification',
                content: 'Deleted an elementFinancement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-element-financement-delete-popup',
    template: ''
})
export class ElementFinancementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private elementFinancementPopupService: ElementFinancementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.elementFinancementPopupService
                .open(ElementFinancementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
