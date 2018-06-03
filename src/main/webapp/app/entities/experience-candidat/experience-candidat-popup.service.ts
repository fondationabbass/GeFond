import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ExperienceCandidat } from './experience-candidat.model';
import { ExperienceCandidatService } from './experience-candidat.service';

@Injectable()
export class ExperienceCandidatPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private experienceCandidatService: ExperienceCandidatService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.experienceCandidatService.find(id)
                    .subscribe((experienceCandidatResponse: HttpResponse<ExperienceCandidat>) => {
                        const experienceCandidat: ExperienceCandidat = experienceCandidatResponse.body;
                        if (experienceCandidat.dateDeb) {
                            experienceCandidat.dateDeb = {
                                year: experienceCandidat.dateDeb.getFullYear(),
                                month: experienceCandidat.dateDeb.getMonth() + 1,
                                day: experienceCandidat.dateDeb.getDate()
                            };
                        }
                        if (experienceCandidat.dateFin) {
                            experienceCandidat.dateFin = {
                                year: experienceCandidat.dateFin.getFullYear(),
                                month: experienceCandidat.dateFin.getMonth() + 1,
                                day: experienceCandidat.dateFin.getDate()
                            };
                        }
                        this.ngbModalRef = this.experienceCandidatModalRef(component, experienceCandidat);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.experienceCandidatModalRef(component, new ExperienceCandidat());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    experienceCandidatModalRef(component: Component, experienceCandidat: ExperienceCandidat): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.experienceCandidat = experienceCandidat;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
