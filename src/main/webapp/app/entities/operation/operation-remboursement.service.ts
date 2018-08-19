import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Operation } from './operation.model';
import { PretService, Pret } from '../pret';
import { dateToNgb } from '../../shared/model/format-utils';

@Injectable()
export class OperationRemboursementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pretService: PretService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, pretId?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }
            this.pretService.find(pretId)
            .subscribe((res: HttpResponse<Pret>) => {
                const operation: Operation  = new Operation();
                operation.pret = res.body;
                operation.typeOperation = 'Remboursement'
                operation.dateOperation = dateToNgb(new Date());
                this.ngbModalRef = this.operationModalRef(component, operation);
                resolve(this.ngbModalRef);
            });
        });
    }

    operationModalRef(component: Component, operation: Operation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.operation = operation;
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
