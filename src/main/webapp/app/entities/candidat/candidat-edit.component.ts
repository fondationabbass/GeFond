import { Component, Input, Output, EventEmitter, ViewChild, OnInit } from '@angular/core';
import { Candidat } from './candidat.model';
import { NgForm } from '@angular/forms';

@Component({
    selector: 'jhi-candidat-edit',
    templateUrl: './candidat-edit.component.html'
})
export class CandidatEditComponent implements OnInit {
    @Input() candidat: Candidat;
    @Output() valid = new EventEmitter<boolean>();
    @ViewChild('editForm') ngForm: NgForm;

    ngOnInit() {
        this.ngForm.form.valueChanges.subscribe(() => {
          this.sendValid(this.ngForm.valid);
        });
    }

    sendValid(val: boolean) {
        this.valid.emit(val);
    }
}
