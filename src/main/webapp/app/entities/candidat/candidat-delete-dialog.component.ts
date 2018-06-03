import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Candidat } from './candidat.model';
import { CandidatPopupService } from './candidat-popup.service';
import { CandidatService } from './candidat.service';

@Component({
    selector: 'jhi-candidat-delete-dialog',
    templateUrl: './candidat-delete-dialog.component.html'
})
export class CandidatDeleteDialogComponent {

    candidat: Candidat;

    constructor(
        private candidatService: CandidatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidatService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candidatListModification',
                content: 'Deleted an candidat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidat-delete-popup',
    template: ''
})
export class CandidatDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidatPopupService: CandidatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candidatPopupService
                .open(CandidatDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
