import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExperienceCandidat } from './experience-candidat.model';
import { ExperienceCandidatPopupService } from './experience-candidat-popup.service';
import { ExperienceCandidatService } from './experience-candidat.service';

@Component({
    selector: 'jhi-experience-candidat-delete-dialog',
    templateUrl: './experience-candidat-delete-dialog.component.html'
})
export class ExperienceCandidatDeleteDialogComponent {

    experienceCandidat: ExperienceCandidat;

    constructor(
        private experienceCandidatService: ExperienceCandidatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experienceCandidatService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'experienceCandidatListModification',
                content: 'Deleted an experienceCandidat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experience-candidat-delete-popup',
    template: ''
})
export class ExperienceCandidatDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private experienceCandidatPopupService: ExperienceCandidatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.experienceCandidatPopupService
                .open(ExperienceCandidatDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
