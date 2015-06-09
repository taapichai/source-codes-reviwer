package com.abctech.reviewer.domain



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SourceReviewerController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SourceReviewer.list(params), model:[sourceReviewerInstanceCount: SourceReviewer.count()]
    }

    def show(SourceReviewer sourceReviewerInstance) {
        respond sourceReviewerInstance
    }

    def create() {
        respond new SourceReviewer(params)
    }

    @Transactional
    def save(SourceReviewer sourceReviewerInstance) {
        if (sourceReviewerInstance == null) {
            notFound()
            return
        }

        if (sourceReviewerInstance.hasErrors()) {
            respond sourceReviewerInstance.errors, view:'create'
            return
        }

        sourceReviewerInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'sourceReviewer.label', default: 'SourceReviewer'), sourceReviewerInstance.id])
                redirect sourceReviewerInstance
            }
            '*' { respond sourceReviewerInstance, [status: CREATED] }
        }
    }

    def edit(SourceReviewer sourceReviewerInstance) {
        respond sourceReviewerInstance
    }

    @Transactional
    def update(SourceReviewer sourceReviewerInstance) {
        if (sourceReviewerInstance == null) {
            notFound()
            return
        }

        if (sourceReviewerInstance.hasErrors()) {
            respond sourceReviewerInstance.errors, view:'edit'
            return
        }

        sourceReviewerInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'SourceReviewer.label', default: 'SourceReviewer'), sourceReviewerInstance.id])
                redirect sourceReviewerInstance
            }
            '*'{ respond sourceReviewerInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(SourceReviewer sourceReviewerInstance) {

        if (sourceReviewerInstance == null) {
            notFound()
            return
        }

        sourceReviewerInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'SourceReviewer.label', default: 'SourceReviewer'), sourceReviewerInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'sourceReviewer.label', default: 'SourceReviewer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
