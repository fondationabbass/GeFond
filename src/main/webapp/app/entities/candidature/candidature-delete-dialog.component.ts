import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Candidature } from './candidature.model';
import { CandidaturePopupService } from './candidature-popup.service';
import { CandidatureService } from './candidature.service';

@Component({
    selector: 'jhi-candidature-delete-dialog',
    templateUrl: './candidature-delete-dialog.component.html'
})
export class CandidatureDeleteDialogComponent {

    candidature: Candidature;

    constructor(
        private candidatureService: CandidatureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidatureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candidatureListModification',
                content: 'Deleted an candidature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidature-delete-popup',
    template: ''
})
export class CandidatureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidaturePopupService: CandidaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candidaturePopupService
                .open(CandidatureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
