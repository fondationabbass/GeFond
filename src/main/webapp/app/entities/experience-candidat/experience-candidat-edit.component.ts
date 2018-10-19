import { Component, Input, Output, EventEmitter, ViewChild, OnInit } from '@angular/core';

import { ExperienceCandidat } from './experience-candidat.model';
import { NgForm } from '@angular/forms';

@Component({
    selector: 'jhi-experience-candidat-edit',
    templateUrl: './experience-candidat-edit.component.html'
})
export class ExperienceCandidatEditComponent implements OnInit {

    @Input() experienceCandidat: ExperienceCandidat;
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
