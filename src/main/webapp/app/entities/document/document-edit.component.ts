import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';

import { Document } from './document.model';
import { NgForm } from '@angular/forms';

@Component({
    selector: 'jhi-document-edit',
    templateUrl: './document-edit.component.html'
})
export class DocumentEditComponent implements OnInit {

    @Input() document: Document;
    @Output() valid = new EventEmitter<boolean>();
    @ViewChild('editForm') ngForm: NgForm;

    ngOnInit() {
        this.ngForm.form.valueChanges.subscribe(x => {
          this.sendValid(this.ngForm.valid);
        })
    }

    sendValid(val: boolean) {
        this.valid.emit(val);
    }
}
