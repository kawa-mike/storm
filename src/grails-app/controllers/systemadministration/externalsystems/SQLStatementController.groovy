/**
 * Copyright (c) 2009/2010, Very Large Business Applications, University Oldenburg. Specifically:
 * Daniel Süpke <suepke@gmx.de>
 * Andreas Solsbach <solsbach@wi-ol.de>
 * Benjamin Wagner vom Berg <wagnervomberg@wi-ol.de>
 * Prof. Dr. Jorge Marx Gomez <jorge.marx.gomez@uni-oldenburg.de>
 * Christian Wenke <cw81@cw81.de>
 * Desislava Dechkova <desislavamd@yahoo.com>
 * Edzard Fisch <edzard.fisch@googlemail.com>
 * Frank Gerken <frank.gerken@uni-oldenburg.de>
 * Gerrit Meerpohl <gerrit.meerpohl@uni-oldenburg.de>
 * Irene Fröhlich <froehlich.irene@web.de>
 * Kerem-Kazim Sezer <Kerem.Sezer@gmx.de>
 * Olaf Roeder <o.roeder@gmx.net>
 * Oliver Norkus <oliver.norkus@googlemail.com>
 * Rachid Lacheheb <rachid.lacheheb@mail.uni-oldenburg.de>
 * Sebastian van Vliet <sebastian.van.vliet@mail.uni-oldenburg.de>
 * Swetlana Lipnitskaya <swetlana.lipnitskaya@uni-oldenburg.de>
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holders nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
 
package systemadministration.externalsystems

/**
 * This class manages the SQL Statements for the ODBC Databases
 * 
 * @author: Gerrit
 * @see: DatabaseController
 */
class SQLStatementController {
	
	//define allowed HTTP-Request methods
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/**
	 * Redirection to list
	 */
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * list SQLStatements
	 */
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[SQLStatementInstanceList: SQLStatement.list(params), SQLStatementInstanceTotal: SQLStatement.count()]
	}
	
	/**
	 * Create SQLStatement
	 */
	def create = {
		def SQLStatementInstance = new SQLStatement()
		SQLStatementInstance.properties = params
		return [SQLStatementInstance: SQLStatementInstance]
	}
	
	/**
	 * Save created SQLStatement
	 */
	def save = {
		def SQLStatementInstance = new SQLStatement(params)
		if (SQLStatementInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), SQLStatementInstance.id])}"
			redirect(action: "show", id: SQLStatementInstance.id)
		}
		else {
			render(view: "create", model: [SQLStatementInstance: SQLStatementInstance])
		}
	}
	
	/**
	 * Show values of SQLStatement
	 */
	def show = {
		def SQLStatementInstance = SQLStatement.get(params.id)
		if (!SQLStatementInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), params.id])}"
			redirect(action: "list")
		}
		else {
			[SQLStatementInstance: SQLStatementInstance]
		}
	}
	
	/**
	 * Edit SQLStatement
	 */
	def edit = {
		def SQLStatementInstance = SQLStatement.get(params.id)
		if (!SQLStatementInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [SQLStatementInstance: SQLStatementInstance]
		}
	}
	
	/**
	 * Update edited SQLStatement
	 */
	def update = {
		def SQLStatementInstance = SQLStatement.get(params.id)
		if (SQLStatementInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (SQLStatementInstance.version > version) {
					
					SQLStatementInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'SQLStatement.label', default: 'SQLStatement')] as Object[], "Another user has updated this SQLStatement while you were editing")
					render(view: "edit", model: [SQLStatementInstance: SQLStatementInstance])
					return
				}
			}
			SQLStatementInstance.properties = params
			if (!SQLStatementInstance.hasErrors() && SQLStatementInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), SQLStatementInstance.id])}"
				redirect(action: "show", id: SQLStatementInstance.id)
			}
			else {
				render(view: "edit", model: [SQLStatementInstance: SQLStatementInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), params.id])}"
			redirect(action: "list")
		}
	}
	
	/**
	 * Delete SQLStatement
	 */
	def delete = {
		def SQLStatementInstance = SQLStatement.get(params.id)
		if (SQLStatementInstance) {
			try {
				SQLStatementInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'SQLStatement.label', default: 'SQLStatement'), params.id])}"
			redirect(action: "list")
		}
	}
}
