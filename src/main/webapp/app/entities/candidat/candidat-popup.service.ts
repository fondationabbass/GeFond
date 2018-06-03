import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Candidat } from './candidat.model';
import { CandidatService } from './candidat.service';

@Injectable()
export class CandidatPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private candidatService: CandidatService

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
                this.candidatService.find(id)
                    .subscribe((candidatResponse: HttpResponse<Candidat>) => {
                        const candidat: Candidat = candidatResponse.body;
                        if (candidat.dateNaissance) {
                            candidat.dateNaissance = {
                                year: candidat.dateNaissance.getFullYear(),
                                month: candidat.dateNaissance.getMonth() + 1,
                                day: candidat.dateNaissance.getDate()
                            };
                        }
                        this.ngbModalRef = this.candidatModalRef(component, candidat);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.candidatModalRef(component, new Candidat());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    candidatModalRef(component: Component, candidat: Candidat): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.candidat = candidat;
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
